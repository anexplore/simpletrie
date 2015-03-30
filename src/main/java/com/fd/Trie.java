package com.fd;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Trie 树,无后缀压缩
 * 
 * @author caoliuyi
 *
 */
@SuppressWarnings("rawtypes")
public class Trie<T extends Comparable> {
	public TrieNode<T> root;

	public Trie() {
		root = new TrieNode<T>();
		root.count = 0L;
	}

	/**
	 * 添加单词
	 * 
	 * @param letters
	 */
	public void addWord(T[] letters) {
		if (letters == null) {
			return;
		}
		TrieNode<T> node = root;
		int len = letters.length - 1;
		for (int i = 0; i < len; i++) {
			node = node.insertChild(letters[i], false);
		}
		node.insertChild(letters[len], true);
	}

	/**
	 * 检查单词是否存在
	 * 
	 * @param letters
	 * @return true if exists
	 */
	public boolean searchWordExists(T[] letters) {
		return searchWord(letters) != null;
	}

	/**
	 * 检查单词是否存在
	 * 
	 * @param letters
	 * @return TrieNode
	 */
	public TrieNode<T> searchWord(T[] letters) {
		if (letters == null) {
			return null;
		}
		TrieNode<T> node = root;
		int len = letters.length;
		int i = 0;
		for (; i < len && node != null; i++) {
			node = node.searchChild(letters[i]);
		}
		if (i == len && node != null && node.end) {
			return node;
		}
		return null;
	}

	/**
	 * 输出遍历路径信息
	 * 
	 * @param route
	 * @param count
	 */
	public void processRoute(List<TrieNode<T>> route, long count) {
		if (route == null) {
			return;
		}
		int len = route.size();
		for (int i = 1; i < len; i++) {
			System.out.print(route.get(i).data);
		}
		System.out.print("\t" + count);
		System.out.println();
	}

	/**
	 * 深度遍历,输出所有结束路径
	 * 
	 * @see #processRoute(List<TrieNode<T>>, long)
	 */
	public void dfs() {
		if (root.childs == null || root.childs.size() == 0) {
			return;
		}
		List<TrieNode<T>> route = new ArrayList<>(20);
		Stack<TrieNode<T>> stack = new Stack<>();
		TrieNode<T> lastVisit = null;
		stack.push(root);
		while (!stack.isEmpty()) {
			TrieNode<T> node = stack.lastElement();
			if (lastVisit != null && node.childs != null
					&& node.childs.get(0) == lastVisit) {
				route.remove(route.size() - 1);
				stack.pop();
				lastVisit = node;
				continue;
			}
			lastVisit = node;
			route.add(node);
			if (node.end) {
				processRoute(route, node.count);
			}
			if (node.childs != null && node.childs.size() > 0) {
				for (TrieNode<T> child : node.childs) {
					stack.push(child);
				}
			} else {
				route.remove(route.size() - 1);
				stack.pop();
			}

		}
	}

	public static void main(String args[]) {
		String[] strs = { "abc/xx.html", "abc/ab.html", "abc/abc.html",
				"abc/abd.html", "ab/xx.html", "http://www.sohu.com" };
		Trie<Character> trie = new Trie<Character>();
		for (String s : strs) {
			char[] array = s.toCharArray();
			Character[] ca = new Character[array.length];
			for (int i = 0; i < array.length; i++) {
				ca[i] = array[i];
			}
			trie.addWord(ca);
		}
		trie.dfs();
		String str = "ab/xx.html";
		char[] strArray = str.toCharArray();
		Character charIns[] = new Character[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			charIns[i] = strArray[i];
		}
		System.out.println(trie.searchWordExists(charIns));
	}

}
