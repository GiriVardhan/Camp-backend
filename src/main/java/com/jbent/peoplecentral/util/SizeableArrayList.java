package com.jbent.peoplecentral.util;

import java.util.ArrayList;

public class SizeableArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3866003515869611295L;

	public int getSize(){
		return this.size();
	}
}
