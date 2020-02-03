package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    Owner owner;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    @Test
    @DisplayName("Create form redirects to correct owner id")
    void processCreationForm_noErrors() {
        final Long ID = 5l;

        // giben
        given(owner.getId()).willReturn(ID);
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(owner)).willReturn(owner); // No poin in testing the mock

        // when
        String result = ownerController.processCreationForm(owner,bindingResult);
        //then
        assertThat(result).isEqualTo("redirect:/owners/"+ID);
        then(ownerService).should().save(owner);
    }

    @Test
    @DisplayName("Create for redirects to update form on error")
    public void testCreateForm_hasErrors() {

        // given
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String result = ownerController.processCreationForm(owner,bindingResult);

        // then
        assertThat(result).isEqualTo(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
        then(ownerService).shouldHaveZeroInteractions();
    }

}