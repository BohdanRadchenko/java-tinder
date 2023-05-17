package org.tinder.models;


import org.tinder.interfaces.Model;

public record User(Integer id, String email) implements Model {
}