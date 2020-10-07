package com.example.balance.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.balance.controller.AccountController;
import com.example.balance.controller.AccountControllerImpl;
import com.example.balance.exception.NotFoundException;
import com.example.balance.exception.BalanceExceededException;
import com.example.balance.model.account.Account;
import com.example.balance.service.account.AccountService;

/**
 * Test for {@link AccountController}
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountControllerImpl.class)
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create() throws Exception {
        String accountNumber = "111111111111111111";

        mockMvc.perform(post("/account/create")
            .param("accountNumber", accountNumber))
            .andExpect(status().isOk());
    }

    @Test
    public void getBalance() throws Exception {
        String accountNumber = "123456789123456789";

        when(accountService.get(eq(accountNumber))).thenReturn(new Account());

        mockMvc.perform(get("/account/balance")
            .param("accountNumber", accountNumber))
            .andExpect(status().isOk());
    }

    @Test
    public void getBalanceWhenAccountNotFound() throws Exception {
        String accountNumber = "000000000000000000";

        when(accountService.get(eq(accountNumber))).thenThrow(new NotFoundException());

        mockMvc.perform(get("/account/balance")
            .param("accountNumber", accountNumber))
            .andExpect(status().isNotFound());
    }

    @Test
    public void replenish() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100";

        mockMvc.perform(put("/account/replenish")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isOk());
    }

    @Test
    public void replenishWhenSumInvalidFormat() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100.123";

        doThrow(new IllegalArgumentException()).when(accountService).replenish(eq(accountNumber), any());

        mockMvc.perform(put("/account/replenish")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void replenishWhenAccountNumberInvalidFormat() throws Exception {
        String accountNumber = "123456  9123456789";
        String sum = "100.12";

        doThrow(new IllegalArgumentException()).when(accountService).replenish(eq(accountNumber), any());

        mockMvc.perform(put("/account/replenish")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void replenishWhenAccountNotFound() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100";

        doThrow(new NotFoundException()).when(accountService).replenish(eq(accountNumber), any());

        mockMvc.perform(put("/account/replenish")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isNotFound());
    }

    @Test
    public void withdraw() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100.00";

        mockMvc.perform(put("/account/withdraw")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isOk());
    }

    @Test
    public void withdrawWhenSumInvalidFormat() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100.123";

        doThrow(new IllegalArgumentException()).when(accountService).withdraw(eq(accountNumber), any());

        mockMvc.perform(put("/account/withdraw")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawWhenAccountNumberInvalidFormat() throws Exception {
        String accountNumber = "123456  9123456789";
        String sum = "100.00";

        doThrow(new IllegalArgumentException()).when(accountService).withdraw(eq(accountNumber), any());

        mockMvc.perform(put("/account/withdraw")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawWhenAccountNotFound() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "1000";

        doThrow(new NotFoundException()).when(accountService).withdraw(eq(accountNumber), any());

        mockMvc.perform(put("/account/withdraw")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isNotFound());
    }

    @Test
    public void withdrawWhenBalanceExceeded() throws Exception {
        String accountNumber = "123456789123456789";
        String sum = "100000";

        doThrow(new BalanceExceededException()).when(accountService).withdraw(eq(accountNumber), any());

        mockMvc.perform(put("/account/withdraw")
            .param("accountNumber", accountNumber)
            .param("sum", sum))
            .andExpect(status().isBadRequest());
    }
}
