package com.rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


public class CursorManager {
    private static final Map<String, Cursor> cursorCache = new HashMap<>();
    
    public static final String CURSOR_DEFAULT = "DEFAULT";
    public static final String CURSOR_HAND = "HAND";
    public static final String CURSOR_CROSSHAIR = "CROSSHAIR";
    public static final String CURSOR_MOVE = "MOVE";
    
    static {
        cursorCache.put(CURSOR_DEFAULT, Cursor.getDefaultCursor());
        cursorCache.put(CURSOR_HAND, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cursorCache.put(CURSOR_CROSSHAIR, Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        cursorCache.put(CURSOR_MOVE, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
    
    /**
     * @param component 
     * @param cursorType 
     */
    public static void setHoverCursor(JComponent component, String cursorType) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                component.setCursor(cursorCache.getOrDefault(cursorType, Cursor.getDefaultCursor()));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                component.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * @param name 
     * @param imagePath 
     * @param hotSpotX 
     * @param hotSpotY 
     */
    public static void registerCustomCursor(String name, String imagePath, int hotSpotX, int hotSpotY) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(CursorManager.class.getResource(imagePath));
            Cursor customCursor = toolkit.createCustomCursor(image, new Point(hotSpotX, hotSpotY), name);
            cursorCache.put(name, customCursor);
        } catch (Exception e) {
            System.err.println("Erro ao carregar cursor personalizado: " + name);
            e.printStackTrace();
        }
    }
    
    /**
     * @param component 
     * @param cursorType 
     */
    public static void setCursor(JComponent component, String cursorType) {
        component.setCursor(cursorCache.getOrDefault(cursorType, Cursor.getDefaultCursor()));
    }
    
    /**
     * @param component 
     */
    public static void setDragCursor(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                component.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * @param component 
     */
    public static void showWaitCursor(JComponent component) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    /**
     * @param component 
     */
    public static void restoreDefaultCursor(JComponent component) {
        component.setCursor(Cursor.getDefaultCursor());
    }
} 