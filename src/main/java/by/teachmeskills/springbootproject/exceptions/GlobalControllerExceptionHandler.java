package by.teachmeskills.springbootproject.exceptions;

import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("state", errors.get(0));
        ModelAndView model = new ModelAndView();
        model.setViewName(PagesPathEnum.REGISTER_PAGE.getPath());
        model.addAllObjects(modelMap);
        return model;
    }
}
