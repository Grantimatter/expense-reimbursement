package com.revature.reimbursements.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
    private final Logger log = LogManager.getLogger(LoggingAspect.class);
    private String logMessage = null;

    /**
     * Logs all methods run within the model package and sub-packages
     * @param joinPoint the specific JoinPoint that this advice is run on
     * @see JoinPoint
     */
    /*@Before("execution(* com.revature.reimbursements.model..*(..))")
    public void logModelMethods(JoinPoint joinPoint) {
        logMessage = String.format("%s invoked %s", joinPoint.getTarget(), joinPoint.getSignature());
        log.debug(logMessage);
    }*/

    @Before("execution(* com.revature.reimbursements.controller..*(..)), execution(* com.revature.reimbursements.model..*(..))")
    public void logControllerMethods(JoinPoint joinPoint) {
        logMessage = String.format("%s invoked %s", joinPoint.getTarget(), joinPoint.getSignature());
        log.debug(logMessage);
    }

    @AfterReturning(value = "within(com.revature.reimbursements.service..*)", returning = "result")
    public void logServiceReturns(JoinPoint joinPoint, Object result) {
        logMessage = String.format("%s invoked %s returning %s", joinPoint.getTarget(), joinPoint.getSignature(), result.toString());
        log.debug(logMessage);
    }

    @AfterThrowing(pointcut = "within(com.revature.reimbursements..*)", throwing = "exception")
    public void logControllerExceptions(JoinPoint joinPoint, Exception exception) {
        logMessage = String.format("%s invoked %s and threw exception %s from %s", joinPoint.getTarget(), joinPoint.getSignature(), exception.getMessage(), exception.getClass());
        log.warn(logMessage);
    }
}
