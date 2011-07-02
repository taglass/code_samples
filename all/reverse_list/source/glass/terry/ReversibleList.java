/*
 *  Author
 *	    Terry Glass
 *  Objective
 *	    Reverse a singley-linked list in place. 
 */

package glass.terry;
import java.lang.StringBuilder;

public class ReversibleList<E> {
    Node<E> f_head;

	public void add(E data) {
        Node<E> node = new Node<E>();
        node.f_data = data;
        node.f_next = f_head;
        f_head = node;
	}

	public void reverse() {
        Node<E> previous = null;
        Node<E> next = null;
        Node<E> current = f_head;

        while(current != null) {
            next = current.f_next;
            current.f_next = previous;
            previous = current;
            current = next;
        }
        f_head = previous;
	}

	@Override
	public String toString() {
        Node<E> current = f_head;
        StringBuilder builder = new StringBuilder();

        while(current != null) {
            builder.append(current);
            builder.append(" ");
            current = current.f_next;
        }
        //Trim the extra space from the end of the string.
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
	}

    private class Node<T> {
        T f_data;
        Node<T> f_next;

        @Override
        public String toString() {
            return f_data.toString();
        }
    }



    public static void main(String[] args) {
        ReversibleList<Integer> list = new ReversibleList<Integer>();

        for(int i = 1; i <= 10; i++) {
            list.add(i);
        }
        
        System.out.println("List contents before reversal: " + list);
        
        list.reverse();
        
        System.out.println("List contents after reversal: " + list);
    }
}
