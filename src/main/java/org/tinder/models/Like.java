package org.tinder.models;


import org.tinder.interfaces.Model;

public record Like(Integer id, Integer from, Integer to, Integer value) implements Model {

}