package org.purple.spring.mybank.deposit;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public record Deposit(@Id @GeneratedValue Long id, int duration) {
}
