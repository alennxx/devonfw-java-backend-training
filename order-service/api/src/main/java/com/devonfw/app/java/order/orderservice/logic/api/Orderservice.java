package com.devonfw.app.java.order.orderservice.logic.api;

import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindCustomer;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindItem;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindOrder;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageCustomer;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageItem;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageOrder;

/**
 * @author NGASIORO
 */
public interface Orderservice
    extends UcFindItem, UcManageItem, UcFindCustomer, UcManageCustomer, UcFindOrder, UcManageOrder {

}
