package org.shop.api.domain;

import lombok.Data;

@Data
public class BaseDo {

    private transient String version;

    private transient String cfrom;

    private transient String channel;

    private transient int pageNo;

    private transient int pageSize = 10;

}
