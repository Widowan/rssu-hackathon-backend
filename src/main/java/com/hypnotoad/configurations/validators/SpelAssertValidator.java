package com.hypnotoad.configurations.validators;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpelAssertValidator implements ConstraintValidator<SpelAssert, Object>, BeanFactoryAware {
    @Autowired SpelExpressionParser spelExpressionParser;
    Expression expr;
    Class<?> cast;
    BeanFactory beanFactory;

    @Override
    public void initialize(SpelAssert constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        expr = spelExpressionParser.parseExpression(constraintAnnotation.expr());
        cast = constraintAnnotation.cast();
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        StandardEvaluationContext ctx = new StandardEvaluationContext(o.getClass().cast(o));

        ctx.setBeanResolver(new BeanFactoryResolver(beanFactory));
        return (boolean) expr.getValue(ctx);
    }
}
