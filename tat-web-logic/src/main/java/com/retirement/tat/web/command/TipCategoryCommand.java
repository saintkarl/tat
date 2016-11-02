package com.retirement.tat.web.command;

import com.retirement.tat.core.dto.TipCategoryDTO;

/**
 * Created by khanhcq on 25-Oct-16.
 */
public class TipCategoryCommand extends AbstractCommand<TipCategoryDTO> {
    public TipCategoryCommand() {
        this.pojo = new TipCategoryDTO();
    }
}
