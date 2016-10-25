package com.retirement.tat.web.controller;

import com.retirement.tat.common.Constants;
import com.retirement.tat.core.data.session.TipLocalBean;
import com.retirement.tat.core.dto.TipDTO;
import com.retirement.tat.web.command.TipCommand;
import com.retirement.tat.web.validator.TipValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by khanhcq on 25-Oct-16.
 */
@Controller
public class TipController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private TipLocalBean tipLocalBean;

    @Autowired
    private TipValidator tipValidator;



    @InitBinder
    public void initBinder(WebDataBinder binder){
    }

    @RequestMapping(value = {"/admin/tip/edit.html"})
    public ModelAndView editTip(@ModelAttribute(value = Constants.FORM_MODEL_KEY) TipCommand command,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/tip/edit");
        TipDTO pojo = command.getPojo();
        try{
            if (Constants.ACTION_INSERT.equals(command.getCrudaction())){
                tipValidator.validate(command, bindingResult);
                if (!bindingResult.hasErrors()){
                    redirectAttributes.addFlashAttribute(Constants.ALTER, Constants.TYLE_SUCCESS);
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("tip.save.successful"));
                    return new ModelAndView("redirect:/admin/tip/list.html");
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            mav.addObject(Constants.ALTER, Constants.TYLE_DANGER);
            mav.addObject(Constants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("database.exception"));
        }
        referencesData(mav, command);
        return mav;
    }

    private void referencesData(ModelAndView mav, TipCommand command) {
        mav.addObject("tipCate", "");
    }
}
