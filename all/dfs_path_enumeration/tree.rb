#Author
# Terry Glass
#Objective
# Enumerate all possible paths through a tree

class TreeNode
  attr_accessor :data, :left_child, :right_child, :parent
  def initialize
  end
end

#Walks the tree applying fun to leaf nodes
def walk(node, fun)
  if node.left_child == nil and node.right_child == nil
    fun.call(node)	
    return
  end

  if node.left_child != nil
    walk(node.left_child, fun)
  end
  if node.right_child != nil
    walk(node.right_child, fun)
  end
end

def print_path(x)
  path = []
  node = x
  while node != nil
    path << node.data
    node = node.parent
  end
  printf("%s\n", path.reverse)
end

#Copypaste together some sample data
def build_sample_tree()
  head = TreeNode.new()
  head.data = 5

  head.left_child = TreeNode.new()
  head.left_child.parent = head
  head.left_child.data = 6

  head.left_child.left_child = TreeNode.new()
  head.left_child.left_child.parent = head.left_child
  head.left_child.left_child.data = 8

  head.right_child = TreeNode.new()
  head.right_child.parent = head
  head.right_child.data = 7
  return head
end


walk(build_sample_tree(), method(:print_path))
