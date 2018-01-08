package de.coding.kata.nim.service.converter;

import de.coding.kata.nim.service.state.GameState;
import de.coding.kata.nim.service.state.StateEnded;
import de.coding.kata.nim.service.state.StateRunning;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GameStateConverter implements AttributeConverter<GameState, String> {

    public enum GameStates {
        RUNNING("RUNNING"), ENDED("ENDED");

        private final String name;

        GameStates(String value) {
            name = value;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public String convertToDatabaseColumn(GameState gameState) {
        return gameState instanceof StateRunning ?GameStates.RUNNING.getName() : GameStates.ENDED.getName();
    }

    @Override
    public GameState convertToEntityAttribute(String s) {
         return GameStates.RUNNING.equals(GameStates.valueOf(s)) ? new StateRunning() : new StateEnded();
    }

}
