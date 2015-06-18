package com.fd.simpletrie;

import java.util.LinkedList;
import java.util.List;

/**
 * Trie 节点 非线程安全
 * 
 * @author caoliuyi
 *
 */
@SuppressWarnings("rawtypes")
public class TrieNode<T extends Comparable> {
	/* 数据 */
	public T data;
	/* 计数 */
	public long count = 1L;
	/* 是否结尾 */
	public boolean end = false;
	/* 有序孩子节点 */
	public List<TrieNode<T>> childs;

	public TrieNode() {
	}

	public TrieNode(T data) {
		this();
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public TrieNode<T> searchChild(T data) {
		if (data == null || childs == null || childs.size() == 0) {
			return null;
		}
		for (TrieNode<T> node : childs) {
			int compare = node.data.compareTo(data); 
			if ( compare == 0) {
				return node;
			}
			if (compare < 0) {
				break;
			}
		}
		return null;
	}
	
	/**
	 * 插入孩子
	 * @param data
	 * @param end
	 * @return
	 */
	public TrieNode<T> insertChild(T data, boolean end) {
		TrieNode<T> node = new TrieNode<T>(data);
		node.setEnd(end);
		return insertChild(node);
	}

	/**
	 * 插入孩子
	 * 所有孩子按照从小到大排序
	 * @param childNode
	 */
	@SuppressWarnings("unchecked")
	public TrieNode<T> insertChild(TrieNode<T> childNode) {
		if (childNode == null || childNode.data == null) {
			return null;
		}
		if (childs == null) {
			childs = new LinkedList<TrieNode<T>>();
		}
		boolean foundPos = false;
		int index = -1;
		TrieNode<T> rnode = childNode;
		for (TrieNode<T> node : childs) {
			index++;
			int compare = childNode.data.compareTo(node.data);
			if (compare > 0) {
				continue;
			} else if (compare == 0) {
				// exist add count
				node.incCount();
				if (childNode.end) {
					node.setEnd(true);
				}
				rnode = node;
				foundPos = true;
				break;
			} else {
				foundPos = true;
				break;
			}
		}
		if (foundPos && rnode == childNode) {
			childs.add(index, childNode);
		}
		if (!foundPos) {
			childs.add(childNode);
		}
		return rnode;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void incCount() {
		count++;
	}
}
