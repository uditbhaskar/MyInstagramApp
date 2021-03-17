package com.mindorks.bootcamp.instagram.utils.common

import com.mindorks.bootcamp.instagram.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.Test

class ValidatorTest {

    @Test
    fun givenValidEmailAndPwd_whenValidate_shouldReturnSuccess(){
        val email ="test@gmail.com"
        val password = "passworwdbw1d"
        val validations = Validator.validateLoginFields(email,password)
        assertThat(validations,hasSize(2))
        assertThat(
                validations,
                contains(
                        Validation(Validation.Field.EMAIL, Resource.success()),
                        Validation(Validation.Field.PASSWORD, Resource.success())
                )
        )
    }

    @Test
    fun givenInvalidEmailAndValidPwd_whenValidate_shouldReturnEmailError(){
        val email ="test"
        val password = "passworwdbw1d"
        val validations = Validator.validateLoginFields(email,password)
        assertThat(validations,hasSize(2))
        assertThat(
                validations,
                contains(
                        Validation(Validation.Field.EMAIL, Resource.error(R.string.email_field_invalid)),
                        Validation(Validation.Field.PASSWORD, Resource.success())
                )
        )
    }

    @Test
    fun givenInvalidPwdANdValidEmail_whenValidate_shouldReturnPasswordError(){
        val email= "test@gmail.com"
        val password= "14e23"
        val validations = Validator.validateLoginFields(email, password)
        assertThat(validations, hasSize(2))
        assertThat(
                validations,
                contains(
                        Validation(Validation.Field.EMAIL, Resource.success()),
                        Validation(Validation.Field.PASSWORD, Resource.error(R.string.password_field_small_length))
                ))
    }


}