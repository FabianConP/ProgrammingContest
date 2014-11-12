package uva;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class _247_Calling_Circles {

	private static class Tarjan {
		ArrayList<Integer>[] ady;
		boolean[] visited;
		int[] dfs_num, dfs_low;
		Stack<Integer> s;
		int dfsNumberCounter, numSCC;
		ArrayList<ArrayList<Integer>> res;

		public Tarjan(int V) {
			ady = new ArrayList[V];
			visited = new boolean[V];
			dfs_num = new int[V];
			dfs_low = new int[V];
			for (int i = 0; i < V; i++) {
				ady[i] = new ArrayList<Integer>();
				dfs_num[i] = -1;
			}
			s = new Stack<Integer>();
			dfsNumberCounter = numSCC = 0;
		}

		public void add(int u, int v) {
			ady[u].add(v);
		}

		public void tarjanSCC(int u) {
			dfs_num[u] = dfs_low[u] = dfsNumberCounter++;
			s.push(u);
			visited[u] = true;
			for (int i = 0; i < ady[u].size(); i++) {
				int v = ady[u].get(i);
				if (dfs_num[v] == -1)
					tarjanSCC(v);
				if (visited[v])
					dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
			}
			if (dfs_low[u] == dfs_num[u]) {
				res.add(new ArrayList<Integer>());
				int v;
				do {
					v = s.pop();
					res.get(numSCC).add(v);
					visited[v] = false;
				} while (u != v);
				numSCC++;
			}
		}

		public ArrayList<ArrayList<Integer>> SCC() {
			res = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < ady.length; i++)
				if (dfs_num[i] == -1)
					tarjanSCC(i);
			return res;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder out = new StringBuilder();
		String line = "";
		int nDataset = 1;
		while ((line = in.readLine()) != null && line.length() != 0) {
			String[] info = line.trim().split(" ");
			int n = Integer.parseInt(info[0]);
			int m = Integer.parseInt(info[1]);
			if (n == 0 && m == 0)
				break;
			Tarjan t = new Tarjan(n);
			int id = 0;
			HashMap<String, Integer> toIds = new HashMap<>(n);
			String[] toNames = new String[n];
			for (int j = 0; j < m; j++) {
				String[] edge = in.readLine().split(" ");
				if (!toIds.containsKey(edge[0])) {
					toIds.put(edge[0], id);
					toNames[id++] = edge[0];
				}
				if (!toIds.containsKey(edge[1])) {
					toIds.put(edge[1], id);
					toNames[id++] = edge[1];
				}
				int a = toIds.get(edge[0]);
				int b = toIds.get(edge[1]);
				t.add(a, b);
			}
			ArrayList<ArrayList<Integer>> res = t.SCC();
			if (nDataset != 1)
				out.append("\n");
			out.append("Calling circles for data set " + nDataset++ + ":\n");
			for (int i = 0; i < res.size(); i++) {
				ArrayList<Integer> circle = res.get(i);
				for (int j = 0; j < circle.size(); j++) {
					if (j != 0)
						out.append(", ");
					out.append(toNames[circle.get(j)]);
				}
				out.append("\n");
			}
		}
		System.out.print(out);
	}
}
