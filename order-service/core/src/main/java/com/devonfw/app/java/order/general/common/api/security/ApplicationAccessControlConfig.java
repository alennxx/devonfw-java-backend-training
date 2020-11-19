package com.devonfw.app.java.order.general.common.api.security;

import javax.inject.Named;

import com.devonfw.module.security.common.api.accesscontrol.AccessControlGroup;
import com.devonfw.module.security.common.base.accesscontrol.AccessControlConfig;

/**
 * Example of {@link AccessControlConfig} that used for testing.
 */
@Named
public class ApplicationAccessControlConfig extends AccessControlConfig {

  public static final String APP_ID = "order-service";

  private static final String PREFIX = APP_ID + ".";

  public static final String PERMISSION_FIND_BINARY_OBJECT = PREFIX + "FindBinaryObject";

  public static final String PERMISSION_SAVE_BINARY_OBJECT = PREFIX + "SaveBinaryObject";

  public static final String PERMISSION_DELETE_BINARY_OBJECT = PREFIX + "DeleteBinaryObject";

  public static final String PERMISSION_FIND_ITEM = PREFIX + "FindItem";

  public static final String PERMISSION_SAVE_ITEM = PREFIX + "SaveItem";

  public static final String PERMISSION_DELETE_ITEM = PREFIX + "DeleteItem";

  public static final String PERMISSION_FIND_CUSTOMER = PREFIX + "FindCustomer";

  public static final String PERMISSION_SAVE_CUSTOMER = PREFIX + "SaveCustomer";

  public static final String PERMISSION_DELETE_CUSTOMER = PREFIX + "DeleteCustomer";

  public static final String PERMISSION_FIND_ORDER = PREFIX + "FindOrder";

  public static final String PERMISSION_SAVE_ORDER = PREFIX + "SaveOrder";

  public static final String PERMISSION_DELETE_ORDER = PREFIX + "DeleteOrder";

  public static final String GROUP_READ_MASTER_DATA = PREFIX + "ReadMasterData";

  public static final String GROUP_ADMIN = PREFIX + "Admin";

  public static final String GROUP_WAITER = PREFIX + "Waiter";

  public static final String GROUP_COOK = PREFIX + "Cook";

  public static final String GROUP_CHIEF = PREFIX + "Chief";

  /**
   * The constructor.
   */
  public ApplicationAccessControlConfig() {

    super();
    AccessControlGroup readMasterData = group(GROUP_READ_MASTER_DATA, PERMISSION_FIND_BINARY_OBJECT);
    group(GROUP_ADMIN, readMasterData, PERMISSION_SAVE_BINARY_OBJECT, PERMISSION_DELETE_BINARY_OBJECT);
    AccessControlGroup waiter = group(GROUP_WAITER, PERMISSION_FIND_ITEM, PERMISSION_FIND_CUSTOMER,
        PERMISSION_SAVE_CUSTOMER, PERMISSION_DELETE_CUSTOMER, PERMISSION_SAVE_ORDER);
    AccessControlGroup cook = group(GROUP_COOK, waiter, PERMISSION_FIND_ORDER, PERMISSION_DELETE_ORDER);
    AccessControlGroup chief = group(GROUP_CHIEF, cook, PERMISSION_SAVE_ITEM, PERMISSION_DELETE_ITEM);
  }

}