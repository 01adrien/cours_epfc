/*
 * This file is part of lanterna (https://github.com/mabe02/lanterna).
 * 
 * lanterna is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2010-2020 Martin Berglund
 */
package com.googlecode.lanterna.gui2;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class is the basic building block for creating user interfaces, being the standard implementation of
 * {@code Container} that supports multiple children. A {@code Panel} is a component that can contain one or more
 * other components, including nested panels. The panel itself doesn't have any particular appearance and isn't
 * interactable by itself, although you can set a border for the panel and interactable components inside the panel will
 * receive input focus as expected.
 *
 * @author Martin
 */
public class Panel extends AbstractComponent<Panel> implements Container {
    private final List<Component> components;
    private LayoutManager layoutManager;
    private TerminalSize cachedPreferredSize;
    private TextColor fillColorOverride;

    /**
     * Default constructor, creates a new panel with no child components and by default set to a vertical
     * {@code LinearLayout} layout manager.
     */
    public Panel() {
        this(new LinearLayout());
    }

    public Panel(LayoutManager layoutManager) {
        if(layoutManager == null) {
            layoutManager = new AbsoluteLayout();
        }
        this.components = new ArrayList<>();
        this.layoutManager = layoutManager;
        this.cachedPreferredSize = null;
    }

    /**
     * Adds a new child component to the panel. Where within the panel the child will be displayed is up to the layout
     * manager assigned to this panel. If the component has already been added to another panel, it will first be
     * removed from that panel before added to this one.
     * @param component Child component to add to this panel
     * @return Itself
     */
    public Panel addComponent(Component component) {
        return addComponent(Integer.MAX_VALUE, component);
    }

    /**
     * Adds a new child component to the panel. Where within the panel the child will be displayed is up to the layout
     * manager assigned to this panel. If the component has already been added to another panel, it will first be
     * removed from that panel before added to this one.
     * @param component Child component to add to this panel
     * @param index At what index to add the component among the existing components
     * @return Itself
     */
    public Panel addComponent(int index, Component component) {
        if(component == null) {
            throw new IllegalArgumentException("Cannot add null component");
        }
        synchronized(components) {
            if(components.contains(component)) {
                return this;
            }
            if(component.getParent() != null) {
                component.getParent().removeComponent(component);
            }
            if (index > components.size()) {
                index = components.size();
            }
            else if (index < 0) {
                index = 0;
            }
            components.add(index, component);
        }
        component.onAdded(this);
        invalidate();
        return this;
    }

    /**
     * This method is a shortcut for calling:
     * <pre>
     *     {@code
     *     component.setLayoutData(layoutData);
     *     panel.addComponent(component);
     *     }
     * </pre>
     * @param component Component to add to the panel
     * @param layoutData Layout data to assign to the component
     * @return Itself
     */
    public Panel addComponent(Component component, LayoutData layoutData) {
        if(component != null) {
            component.setLayoutData(layoutData);
            addComponent(component);
        }
        return this;
    }

    @Override
    public boolean containsComponent(Component component) {
        return component != null && component.hasParent(this);
    }

    @Override
    public boolean removeComponent(Component component) {
        if(component == null) {
            throw new IllegalArgumentException("Cannot remove null component");
        }
        synchronized(components) {
            int index = components.indexOf(component);
            if(index == -1) {
                return false;
            }
            if(getBasePane() != null && getBasePane().getFocusedInteractable() == component) {
                getBasePane().setFocusedInteractable(null);
            }
            components.remove(index);
        }
        component.onRemoved(this);
        invalidate();
        return true;
    }

    /**
     * Removes all child components from this panel
     * @return Itself
     */
    public Panel removeAllComponents() {
        synchronized(components) {
            for(Component component : new ArrayList<>(components)) {
                removeComponent(component);
            }
        }
        return this;
    }

    /**
     * Assigns a new layout manager to this panel, replacing the previous layout manager assigned. Please note that if
     * the panel is not empty at the time you assign a new layout manager, the existing components might not show up
     * where you expect them and their layout data property might need to be re-assigned.
     * @param layoutManager New layout manager this panel should be using
     * @return Itself
     */
    public synchronized Panel setLayoutManager(LayoutManager layoutManager) {
        if(layoutManager == null) {
            layoutManager = new AbsoluteLayout();
        }
        this.layoutManager = layoutManager;
        invalidate();
        return this;
    }

    /**
     * Returns the color used to override the default background color from the theme, if set. Otherwise {@code null} is
     * returned and whatever theme is assigned will be used to derive the fill color.
     * @return The color, if any, used to fill the panel's unused space instead of the theme's color
     */
    public TextColor getFillColorOverride() {
        return fillColorOverride;
    }

    /**
     * Sets an override color to be used instead of the theme's color for Panels when drawing unused space. If called
     * with {@code null}, it will reset back to the theme's color.
     * @param fillColor Color to draw the unused space with instead of what the theme definition says, no {@code null}
     *                  to go back to the theme definition
     */
    public void setFillColorOverride(TextColor fillColor) {
        this.fillColorOverride = fillColor;
    }

    /**
     * Returns the layout manager assigned to this panel
     * @return Layout manager assigned to this panel
     */
    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public int getChildCount() {
        synchronized(components) {
            return components.size();
        }
    }

    @Override
    public Collection<Component> getChildren() {
        return getChildrenList();
    }

    @Override
    public List<Component> getChildrenList() {
        synchronized(components) {
            return new ArrayList<>(components);
        }
    }

    @Override
    protected ComponentRenderer<Panel> createDefaultRenderer() {
        return new DefaultPanelRenderer();
    }

    public class DefaultPanelRenderer implements ComponentRenderer<Panel> {
        private boolean fillAreaBeforeDrawingComponents = true;

        /**
         * If setting this to {@code false} (default is {@code true}), the {@link Panel} will not reset it's drawable
         * area with the space character ' ' before drawing all the components. Usually you <b>do</b> want to reset this
         * area before drawing but you might have a custom renderer that has prepared the area already and just want the
         * panel renderer to layout and draw the components in the panel without touching the existing content. One such
         * example is the {@code FullScreenTextGUITest}.
         * @param fillAreaBeforeDrawingComponents Should the panels area be cleared before drawing components?
         */
        public void setFillAreaBeforeDrawingComponents(boolean fillAreaBeforeDrawingComponents) {
            this.fillAreaBeforeDrawingComponents = fillAreaBeforeDrawingComponents;
        }

        @Override
        public TerminalSize getPreferredSize(Panel component) {
            synchronized(components) {
                cachedPreferredSize = layoutManager.getPreferredSize(components);
            }
            return cachedPreferredSize;
        }

        @Override
        public void drawComponent(TextGUIGraphics graphics, Panel panel) {
            if(isInvalid()) {
                layout(graphics.getSize());
            }

            if (fillAreaBeforeDrawingComponents) {
                // Reset the area
                graphics.applyThemeStyle(getThemeDefinition().getNormal());
                if (fillColorOverride != null) {
                    graphics.setBackgroundColor(fillColorOverride);
                }
                graphics.fill(' ');
            }

            synchronized(components) {
                for(Component child: components) {
                    if (!child.isVisible()) {
                        continue;
                    }
                    TextGUIGraphics componentGraphics = graphics.newTextGraphics(child.getPosition(), child.getSize());
                    child.draw(componentGraphics);
                }
            }
        }
    }

    @Override
    public TerminalSize calculatePreferredSize() {
        if(cachedPreferredSize != null && !isInvalid()) {
            return cachedPreferredSize;
        }
        return super.calculatePreferredSize();
    }

    @Override
    public boolean isInvalid() {
        synchronized(components) {
            for(Component component: components) {
                if(component.isVisible() && component.isInvalid()) {
                    return true;
                }
            }
        }
        return super.isInvalid() || layoutManager.hasChanged();
    }    

    @Override
    public Interactable nextFocus(Interactable fromThis) {
        boolean chooseNextAvailable = (fromThis == null);

        synchronized(components) {
            for(Component component : components) {
                if(!component.isVisible()) {
                    continue;
                }
                if(chooseNextAvailable) {
                    if(component instanceof Interactable && ((Interactable) component).isEnabled() && ((Interactable) component).isFocusable()) {
                        return (Interactable) component;
                    }
                    else if(component instanceof Container) {
                        Interactable firstInteractable = ((Container) (component)).nextFocus(null);
                        if(firstInteractable != null) {
                            return firstInteractable;
                        }
                    }
                    continue;
                }

                if(component == fromThis) {
                    chooseNextAvailable = true;
                    continue;
                }

                if(component instanceof Container) {
                    Container container = (Container) component;
                    if(fromThis.isInside(container)) {
                        Interactable next = container.nextFocus(fromThis);
                        if(next == null) {
                            chooseNextAvailable = true;
                        }
                        else {
                            return next;
                        }
                    }
                }
            }
            return null;
        }
    }

    @Override
    public Interactable previousFocus(Interactable fromThis) {
        boolean chooseNextAvailable = (fromThis == null);

        List<Component> reversedComponentList;
        synchronized(components) {
            reversedComponentList = new ArrayList<>(components);
        }
        Collections.reverse(reversedComponentList);

        for (Component component : reversedComponentList) {
            if (!component.isVisible()) {
                continue;
            }
            if (chooseNextAvailable) {
                if (component instanceof Interactable && ((Interactable)component).isEnabled() && ((Interactable)component).isFocusable()) {
                    return (Interactable) component;
                }
                if (component instanceof Container) {
                    Interactable lastInteractable = ((Container)(component)).previousFocus(null);
                    if (lastInteractable != null) {
                        return lastInteractable;
                    }
                }
                continue;
            }

            if (component == fromThis) {
                chooseNextAvailable = true;
                continue;
            }

            if (component instanceof Container) {
                Container container = (Container) component;
                if (fromThis.isInside(container)) {
                    Interactable next = container.previousFocus(fromThis);
                    if (next == null) {
                        chooseNextAvailable = true;
                    } else {
                        return next;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        return false;
    }
    
    @Override
    public void updateLookupMap(InteractableLookupMap interactableLookupMap) {
        synchronized(components) {
            for(Component component: components) {
                if(!component.isVisible()) {
                    continue;
                }
                if(component instanceof Container) {
                    ((Container)component).updateLookupMap(interactableLookupMap);
                }
                else if(component instanceof Interactable && ((Interactable)component).isEnabled() && ((Interactable)component).isFocusable()) {
                    interactableLookupMap.add((Interactable)component);
                }
            }
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        synchronized(components) {
            //Propagate
            for(Component component: components) {
                component.invalidate();
            }
        }
    }

    private void layout(TerminalSize size) {
        synchronized(components) {
            layoutManager.doLayout(size, components);
        }
    }

    public Panel addEmpty() {
        return addEmpty(TerminalSize.ONE);
    }

    public Panel addEmpty(TerminalSize size) {
        new EmptySpace(size).addTo(this);
        return this;
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     * Par défaut, les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel verticalPanel(Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.VERTICAL, 0, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants verticalement et ajoute les composants reçus en paramètre.
     * Par défaut, les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asVerticalPanel(Component... components) {
        return configPanel(this, LinearLayout.Alignment.Center, Direction.VERTICAL, 0, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     * Les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public static Panel verticalPanel(int spacing, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.VERTICAL, spacing, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants verticalement et ajoute les composants reçus en paramètre.
     * Par défaut, les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public Panel asVerticalPanel(int spacing, Component... components) {
        return configPanel(this, LinearLayout.Alignment.Center, Direction.VERTICAL, spacing, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     *
     * @param alignment  la direction d'alignement horizontal des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel verticalPanel(LinearLayout.Alignment alignment,
                                      Component... components) {
        return createPanel(alignment, Direction.VERTICAL, 0, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants verticalement et ajoute les composants reçus en paramètre.
     *
     * @param alignment  la direction d'alignement horizontal des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asVerticalPanel(LinearLayout.Alignment alignment, Component... components) {
        return configPanel(this, alignment, Direction.VERTICAL, 0, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     * Par défaut, les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel horizontalPanel(Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.HORIZONTAL, 1, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants horizontalement et ajoute les composants reçus en paramètre.
     * Par défaut, les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asHorizontalPanel(Component... components) {
        return configPanel(this, LinearLayout.Alignment.Center, Direction.HORIZONTAL, 1, components);
    }


    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     * Par défaut, les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public static Panel horizontalPanel(int spacing, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.HORIZONTAL, spacing, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants horizontalement et ajoute les composants reçus en paramètre.
     * Par défaut, les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public Panel asHorizontalPanel(int spacing, Component... components) {
        return configPanel(this, LinearLayout.Alignment.Center, Direction.HORIZONTAL, spacing, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     *
     * @param alignment  la direction d'alignement vertical des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel horizontalPanel(LinearLayout.Alignment alignment,
                                        Component... components) {
        return createPanel(alignment, Direction.HORIZONTAL, 1, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants horizontalement et ajoute les composants reçus en paramètre.
     *
     * @param alignment  la direction d'alignement vertical des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asHorizontalPanel(LinearLayout.Alignment alignment, Component... components) {
        return configPanel(this, alignment, Direction.HORIZONTAL, 1, components);
    }

    private static Panel createPanel(LinearLayout.Alignment alignment, Direction direction, int spacing,
                                     Component... components) {
        return configPanel(new Panel(), alignment, direction, spacing, components);
    }

    private static Panel configPanel(Panel panel, LinearLayout.Alignment alignment, Direction direction, int spacing,
                                     Component... components) {
        var layout = LinearLayout.createLayoutData(alignment);
        panel.setLayoutManager(new LinearLayout(direction).setSpacing(spacing));
        for (var component : components)
            panel.addComponent(component, layout);
        return panel;
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant en grille.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel gridPanel(int numberOfColumns,
                                  Component... components) {
        return createGridPanel(numberOfColumns, Margin.getDefault(), Spacing.getDefault(), components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants en grille et ajoute les composants reçus en paramètre.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asGridPanel(int numberOfColumns, Component... components) {
        return configGridPanel(this, numberOfColumns, Margin.getDefault(), Spacing.getDefault(), components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant en grille.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param margin          la marge à appliquer autour du panel
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel gridPanel(int numberOfColumns, Margin margin,
                                  Component... components) {
        return createGridPanel(numberOfColumns, margin, Spacing.getDefault(), components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants en grille et ajoute les composants reçus en paramètre.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param margin          la marge à appliquer autour du panel
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asGridPanel(int numberOfColumns, Margin margin, Component... components) {
        return configGridPanel(this, numberOfColumns, margin, Spacing.getDefault(), components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant en grille.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param spacing         l'espacement entre les composants enfants
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel gridPanel(int numberOfColumns, Spacing spacing,
                                  Component... components) {
        return createGridPanel(numberOfColumns, Margin.getDefault(), spacing, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants en grille et ajoute les composants reçus en paramètre.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param spacing         l'espacement entre les composants enfants
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asGridPanel(int numberOfColumns, Spacing spacing, Component... components) {
        return configGridPanel(this, numberOfColumns, Margin.getDefault(), spacing, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant en grille.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param margin          la marge à appliquer autour du panel
     * @param spacing         l'espacement entre les composants enfants
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel gridPanel(int numberOfColumns, Margin margin,
                                  Spacing spacing, Component... components) {
        return createGridPanel(numberOfColumns, margin, spacing, components);
    }

    /**
     * Transforme le panel pour qu'il aligne ses composants en grille et ajoute les composants reçus en paramètre.
     *
     * @param numberOfColumns le nombre de colonnes de la grille
     * @param margin          la marge à appliquer autour du panel
     * @param spacing         l'espacement entre les composants enfants
     * @param components      les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public Panel asGridPanel(int numberOfColumns, Margin margin, Spacing spacing,
                                    Component... components) {
        return configGridPanel(this, numberOfColumns, margin, spacing, components);
    }

    private static Panel createGridPanel(int numberOfColumns, Margin margin, Spacing spacing, Component... components) {
        return configGridPanel(new Panel(), numberOfColumns, margin, spacing, components);
    }

    private static Panel configGridPanel(Panel panel, int numberOfColumns, Margin margin, Spacing spacing,
                                         Component... components) {
        panel.setLayoutManager(new GridLayout(numberOfColumns)
                .setTopMarginSize(margin.getTop())
                .setBottomMarginSize(margin.getBottom())
                .setLeftMarginSize(margin.getLeft())
                .setRightMarginSize(margin.getRight())
                .setVerticalSpacing(spacing.getVertical())
                .setHorizontalSpacing(spacing.getHorizontal())
        );
        for (var component : components)
            component.addTo(panel);
        return panel;
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les plaçant dans un panel de type BorderLayout.
     * Les composants enfants sont ajoutés dans l'ordre aux emplacements respectifs  suivant :
     * milieu, dessus, dessous, gauche et droite.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static Panel borderPanel(Component... components) {
        return createBorderPanel(components);
    }


    public Panel asBorderPanel(Component... components) {
        return configBorderPanel(this, components);
    }

    private static Panel createBorderPanel(Component... components) {
        return configBorderPanel(new Panel(), components);
    }

    private static Panel configBorderPanel(Panel panel, Component... components) {
        panel.setLayoutManager(new BorderLayout());
        for (var component : components)
            component.addTo(panel);
        return panel;
    }
}
