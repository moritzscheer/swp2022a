package de.uol.swp.client.lobby.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.security.SecureRandom;

/**
 * Show Lobby Event
 *
 * @author Ole Zimmermann & Maxim Erden
 * @since 2023-02-27
 */
public class Card {

    private Rectangle position;
    private CardType cardType;
    private int value;

    private ImagePattern picture;

    private static final SecureRandom random = new SecureRandom();

    public Card(int value) {

        this.value = value;
        this.cardType = createCardType(this.value);
        this.picture = this.createPicture();
    }

    public CardType createCardType(int value) {

        if (value <= 60) {
            return CardType.UTURN;
        }
        if (value <= 420) {
            if (value / 10 % 2 == 0) {
                return CardType.RIGHTTURN;
            } else if (value / 10 % 2 == 1) {
                return CardType.LEFTTURN;
            }
        }
        if (value <= 480) {
            return CardType.BACKUP;
        }
        if (value <= 660) {
            return CardType.MOVE1;
        }
        if (value <= 780) {
            return CardType.MOVE2;
        }
        if (value <= 840) {
            return CardType.MOVE3;
        }
        return null;
    }

    public ImagePattern createPicture() {
        ImagePattern result;

        switch (this.cardType) {
            case MOVE1:
                result = new ImagePattern(new Image("images/cards/prgcard006.png"));
                break;

            case MOVE2:
                result = new ImagePattern(new Image("images/cards/prgcard007.png"));
                break;

            case MOVE3:
                result = new ImagePattern(new Image("images/cards/prgcard005.png"));
                break;

            case UTURN:
                result = new ImagePattern(new Image("images/cards/prgcard004.png"));
                break;

            case BACKUP:
                result = new ImagePattern(new Image("images/cards/prgcard008.png"));
                break;

            case LEFTTURN:
                result = new ImagePattern(new Image("images/cards/prgcard001.png"));
                break;

            case RIGHTTURN:
                result = new ImagePattern(new Image("images/cards/prgcard003.png"));
                break;

            default:
                result = new ImagePattern(new Image("images/cards/prgcard002.png"));
                break;
        }
        return result;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ImagePattern getPicture() {
        return picture;
    }

    public void setPicture(ImagePattern picture) {
        this.picture = picture;
    }
}
