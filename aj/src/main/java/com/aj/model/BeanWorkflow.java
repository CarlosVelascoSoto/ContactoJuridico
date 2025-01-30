package com.aj.model;

import java.io.Serializable;
import java.util.List;

public class BeanWorkflow implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Wfprocessstage> lwfp;
	private List<com.aj.model.Process> lp;

	public List<Wfprocessstage> getLwfp() {
		return lwfp;
	}

	public void setLwfp(List<Wfprocessstage> lwfp) {
		this.lwfp = lwfp;
	}

	public List<com.aj.model.Process> getLp() {
		return lp;
	}

	public void setLp(List<com.aj.model.Process> lp) {
		this.lp = lp;
	}

}
