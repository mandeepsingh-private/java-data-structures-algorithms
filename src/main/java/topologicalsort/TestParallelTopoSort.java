package topologicalsort;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class DependencyManager {
	private final ConcurrentHashMap<String, List<String>> _dependencies = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, List<String>> _reverseDependencies = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Runnable> _tasks = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Integer> _numDependenciesExecuted = new ConcurrentHashMap<>();
	private final AtomicInteger _numTasksExecuted = new AtomicInteger(0);
	private final ExecutorService _executorService = Executors
			.newFixedThreadPool(16);

	private static Runnable getRunnable(DependencyManager dependencyManager,
			String taskId) {
		return () -> {
			try {
				Thread.sleep(2000); // A task takes 2 seconds to finish.
				dependencyManager.taskCompleted(taskId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}

	/**
	 * In case a vertex is disconnected from the rest of the graph.
	 * 
	 * @param taskId The task id
	 */
	public void addVertex(String taskId) {
		_dependencies.putIfAbsent(taskId, new ArrayList<>());
		_reverseDependencies.putIfAbsent(taskId, new ArrayList<>());
		_tasks.putIfAbsent(taskId, getRunnable(this, taskId));
		_numDependenciesExecuted.putIfAbsent(taskId, 0);
	}

	private void addEdge(String dependentTaskId, String dependeeTaskId) {
		_dependencies.get(dependentTaskId).add(dependeeTaskId);
		_reverseDependencies.get(dependeeTaskId).add(dependentTaskId);
	}

	public void addDependency(String dependentTaskId, String dependeeTaskId) {
		addVertex(dependentTaskId);
		addVertex(dependeeTaskId);
		addEdge(dependentTaskId, dependeeTaskId);
	}

	private void taskCompleted(String taskId) {
		System.out.println(
				String.format("%s:: Task %s done!!", Instant.now(), taskId));
		_numTasksExecuted.incrementAndGet();
		_reverseDependencies.get(taskId).forEach(nextTaskId -> {
			_numDependenciesExecuted.computeIfPresent(nextTaskId,
					(__, currValue) -> currValue + 1);
			int numDependencies = _dependencies.get(nextTaskId).size();
			int numDependenciesExecuted = _numDependenciesExecuted
					.get(nextTaskId);
			if (numDependenciesExecuted == numDependencies) {
				// All dependencies have been executed, so we can submit this
				// task to the threadpool.
				_executorService.submit(_tasks.get(nextTaskId));
			}
		});
		if (_numTasksExecuted.get() == _tasks.size()) {
			topoSortCompleted();
		}
	}

	private void topoSortCompleted() {
		System.out.println("Topo sort complete!!");
		_executorService.shutdownNow();
	}

	public void executeTopoSort() {
		System.out.println(
				String.format("%s:: Topo sort started!!", Instant.now()));
		_dependencies.forEach((taskId, dependencies) -> {
			if (dependencies.isEmpty()) {
				_executorService.submit(_tasks.get(taskId));
			}
		});
	}
}

public class TestParallelTopoSort {

	public static void main(String[] args) {
		DependencyManager dependencyManager = new DependencyManager();
		dependencyManager.addDependency("8", "5");
		dependencyManager.addDependency("7", "5");
		dependencyManager.addDependency("7", "6");
		dependencyManager.addDependency("6", "3");
		dependencyManager.addDependency("6", "4");
		dependencyManager.addDependency("5", "1");
		dependencyManager.addDependency("5", "2");
		dependencyManager.addDependency("5", "3");
		dependencyManager.addDependency("4", "1");
		dependencyManager.executeTopoSort();
		// Parallel version takes 8 seconds to execute.
		// Serial version would have taken 16 seconds.

	}
}