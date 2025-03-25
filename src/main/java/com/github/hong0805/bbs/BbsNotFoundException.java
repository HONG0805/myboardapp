package com.github.hong0805.bbs;

public class BbsNotFoundException extends RuntimeException {
	public BbsNotFoundException(Long bbsID) {
		super("Could not find bbs with id: " + bbsID);
	}
}