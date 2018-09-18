public class GenericArrayStack<E> implements Stack<E> {
    private int capacity;
    private E  stackArray [];
    private int size=0;

   // Constructor
    public GenericArrayStack( int capacity ) {
        this.capacity=capacity;
        stackArray=(E[])new Object [capacity];
        // E[] ARR=(E[])new Object[INITIAL_ARRAY_LENGTH];
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        if (size==0){
            return true;
        }
        return false;

    }

    public void push( E elem ) {
        if (size==stackArray.length){
            doubleCapacity();
        }
        stackArray[size]=elem;
        size++;

    }
    public E pop() {
        E obj;
        if (isEmpty()==true) {
            System.out.println ("Stack is Empty");
        }
        obj=stackArray[size-1];
        stackArray[size-1]=null;
        size--;
        return obj;
    }

    public E peek() {
        if (isEmpty()==true) {
            System.out.println ("Stack is Empty, nothing to peek");
        }
        return stackArray[size-1];
    }
    private void doubleCapacity (){
        int doubleCapacity=2*capacity;
        E [] newStack= (E[]) new Object [doubleCapacity];
        //(E[])new Object[INITIAL_ARRAY_LENGTH];
        for (int i =0; i<stackArray.length;i++){
            newStack[i]=stackArray[i];
        }
        stackArray=newStack;
    }
}
