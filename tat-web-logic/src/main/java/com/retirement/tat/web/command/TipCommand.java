package com.retirement.tat.web.command;

import com.retirement.tat.core.dto.TipDTO;

/**
 * Created by khanhcq on 25-Oct-16.
 */
public class TipCommand extends AbstractCommand<TipDTO> {
    public TipCommand() {
        this.pojo = new TipDTO();
    }
}
