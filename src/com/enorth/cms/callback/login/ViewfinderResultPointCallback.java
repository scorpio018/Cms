package com.enorth.cms.callback.login;

import com.enorth.cms.widget.view.login.ViewfinderView;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public class ViewfinderResultPointCallback implements ResultPointCallback {

	  private final ViewfinderView viewfinderView;

	  public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
	    this.viewfinderView = viewfinderView;
	  }

	  public void foundPossibleResultPoint(ResultPoint point) {
	    viewfinderView.addPossibleResultPoint(point);
	  }

}
