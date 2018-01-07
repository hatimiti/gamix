package com.github.hatimiti.gamix.app.game.di.service;

import javax.annotation.Resource;

import com.github.hatimiti.gamix.app.dbflute.exbhv.TcmSampleBhv;

public class SampleService {
	
	@Resource
	public TcmSampleBhv tcmSampleBhv;
	
	public void execute() {
		System.out.println(this.tcmSampleBhv.selectByPK(1L));
	}

}