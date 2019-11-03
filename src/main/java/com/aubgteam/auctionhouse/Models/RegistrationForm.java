package com.aubgteam.auctionhouse.Models;

import lombok.Data;

import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;
@Data
public class RegistrationForm {

    private String username;

    private String name;

    private String email;

    @Transient
    private String passwordConfirm;

    private String password;

    private long credit_card_id;

    private String credit_card_number;

    private Date expire_date;

    private String cvv;

    private String owner_name;

    private String billing_address;


}
