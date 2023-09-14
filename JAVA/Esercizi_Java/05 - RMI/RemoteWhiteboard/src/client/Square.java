package client;

import whiteboard.Shape;

public class Square implements Shape{

    private static final long serialVersionUID = 1L;

    @Override
    public void draw() {
        System.out.println ("+--+");
		System.out.println ("|  |");
		System.out.println ("+--+");
    }
    
}
