# Dynamic Connectivity Algorithms: Summary

## Table: Time Complexities of Dynamic Connectivity Algorithms

| Algorithm                                      | Find Operation Time Complexity | Union Operation Time Complexity | Overall Time Complexity for M Operations |
| ---------------------------------------------- | ------------------------------ | ------------------------------- | ---------------------------------------- |
| **Quick-Find**                                 | `O(1)`                         | `O(N)`                          | `O(M * N)`                               |
| **Quick-Union**                                | `O(N)`                         | `O(N)`                          | `O(M * N)`                               |
| **Weighted Quick-Union**                       | `O(log N)`                     | `O(log N)`                      | `O(M * log N)`                           |
| **Weighted Quick-Union with Path Compression** | `O(log* N)`                    | `O(log* N)`                     | `O(M * log* N)`                          |

## The Idea Behind Each Algorithm's Improvement

1. **Quick-Find**:

   - **Idea**: Start with a simple approach where each element has a unique ID, making `find` operations fast but `union` operations slow due to the need to update many entries.

2. **Quick-Union**:

   - **Improvement**: Instead of updating multiple entries, represent components as trees, where each element points to a parent, reducing the scope of changes needed during `union`.

3. **Weighted Quick-Union**:

   - **Improvement**: Ensure trees remain balanced by always attaching the smaller tree to the root of the larger tree during `union`, reducing the tree's maximum height and improving efficiency.

4. **Weighted Quick-Union with Path Compression**:
   - **Improvement**: Further optimize by flattening the tree whenever `find` is called, making future operations faster and keeping the tree height almost constant, even for large datasets.

## How Each Algorithm "Works" (Structures and Approach)

1. **Quick-Find**:

   - **Structure**: An array `id[]` where each index represents an element, and the value represents the component ID.
   - **Approach**: All elements in the same component have the same ID value.

2. **Quick-Union**:

   - **Structure**: An array `parent[]` where each index represents an element, and the value points to the parent of that element.
   - **Approach**: Elements form a tree, and the root represents the component ID.

3. **Weighted Quick-Union**:

   - **Structure**: Same as Quick-Union, but with additional `size[]` array to keep track of the size of each tree.
   - **Approach**: Smaller trees are attached under the root of larger trees to keep them balanced.

4. **Weighted Quick-Union with Path Compression**:
   - **Structure**: Same as Weighted Quick-Union, but with path compression during `find`.
   - **Approach**: Flatten the tree during `find` by making all nodes along the path point directly to the root, improving efficiency.

## How Each Operation in Each Algorithm Works

1. **Quick-Find**:

   - **Find**: Check if two elements have the same value in the `id` array.
   - **Union**: Update all entries in the `id` array that match one element's component to match the other.

2. **Quick-Union**:

   - **Find**: Follow parent pointers until reaching a root (an element that points to itself).
   - **Union**: Connect the root of one element to the root of another by updating the parent pointer.

3. **Weighted Quick-Union**:

   - **Find**: Same as Quick-Union, follow parent pointers until reaching the root.
   - **Union**: Link the root of the smaller tree to the root of the larger tree to keep trees balanced.

4. **Weighted Quick-Union with Path Compression**:
   - **Find**: Same as Weighted Quick-Union, but also make nodes point directly to the root during traversal, flattening the tree.
   - **Union**: Same as Weighted Quick-Union, linking the smaller tree to the larger tree's root.
     e
