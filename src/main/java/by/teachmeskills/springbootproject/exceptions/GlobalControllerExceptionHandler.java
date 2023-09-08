package by.teachmeskills.springbootproject.exceptions;

import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DBConnectionException.class)
    public ModelAndView handleDBConnectionException(DBConnectionException ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("error", ex.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(PagesPathEnum.ERROR_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoOrderAddressException.class)
    public ModelAndView handleNoOrderAddressException(NoOrderAddressException ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("state", ex.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName(PagesPathEnum.CHECKOUT_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }
}
