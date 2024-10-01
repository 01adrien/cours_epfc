package com.googlecode.lanterna.gui2;

public interface LayoutHelper<T> {

    /**
     *  Permet de centrer un composant dans un panel ayant un LinearLayout. Cet alignement est réalisé
     *  par rapport à l'axe secondaire du LinearLayout, l'axe principal étant spécifié au moment
     *  de la création du LinearLayout. Par exemple, si le LinearLayout est vertical, le composant sera
     *  centré horizontalement. A l'inverse, si le LinearLayout est horizontal, le composant sera centré
     *  verticalement.
     */
    T center();

    /**
     *  Permet de placer un composant au début d'un panel ayant un LinearLayout. Cet alignement est réalisé
     *  par rapport à l'axe secondaire du LinearLayout, l'axe principal étant spécifié au moment
     *  de la création du LinearLayout. Par exemple, si le LinearLayout est vertical, le composant sera
     *  placé à gauche. A l'inverse, si le LinearLayout est horizontal, le composant sera placé en haut.
     */
    T begin();

    /**
     *  Permet de placer un composant à la fin d'un panel ayant un LinearLayout. Cet alignement est réalisé
     *  par rapport à l'axe secondaire du LinearLayout, l'axe principal étant spécifié au moment
     *  de la création du LinearLayout. Par exemple, si le LinearLayout est vertical, le composant sera
     *  placé à droite. A l'inverse, si le LinearLayout est horizontal, le composant sera placé en bas.
     */
    T end();

    /**
     *  Permet de placer un composant en lui faisant occuper toute la place disponible au sein  d'un panel ayant
     *  un LinearLayout. Cet alignement est réalisé par rapport à l'axe principal du LinearLayout, l'axe secondaire
     *  étant spécifié au moment de la création du LinearLayout. Par exemple, si le LinearLayout est vertical, le
     *  composant occupera toute la largeur. A l'inverse, si le LinearLayout est horizontal, le composant occupera
     *  toute la hauteur.
     */
    T fill();

    /**
     *  Permet de préciser si un composant doit être redimensionné pour occuper toute la place disponible dans
     *  la direction principale d'un panel ayant un LinearLayout. Par exemple, si le LinearLayout est vertical, le
     *  composant sera redimensionné en hauteur. A l'inverse, si le LinearLayout est horizontal, le composant sera
     *  redimensionné en largeur. Si plusieurs composants ont la propriété grow activée, ils se partageront l'espace
     *  disponible de manière proportionnelle.
     *  @param grow Indique si le composant doit être redimensionné
     */
    T grow(boolean grow);

    /**
     *  Permet de préciser qu'un composant doit être redimensionné pour occuper toute la place disponible dans
     *  la direction principale d'un panel ayant un LinearLayout. Par exemple, si le LinearLayout est vertical, le
     *  composant sera redimensionné en hauteur. A l'inverse, si le LinearLayout est horizontal, le composant sera
     *  redimensionné en largeur. Si plusieurs composants ont la propriété grow activée, ils se partageront l'espace
     *  disponible de manière proportionnelle.
     */
    T grow();

    /**
     *  Permet de préciser qu'un composant doit être aligné en haut et à gauche d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T topLeft();

    /**
     *  Permet de préciser qu'un composant doit être aligné en haut et au centre d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T topCenter();

    /**
     *  Permet de préciser qu'un composant doit être aligné en haut et à droite d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T topRight();

    /**
     *  Permet de préciser qu'un composant doit être aligné en haut et occuper toute la largeur d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T topFill();

    /**
     *  Permet de préciser qu'un composant doit être aligné au centre et à gauche d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T centerLeft();

    /**
     *  Permet de préciser qu'un composant doit être aligné au centre et au centre d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T centerCenter();

    /**
     *  Permet de préciser qu'un composant doit être aligné au centre et à droite d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T centerRight();

    /**
     *  Permet de préciser qu'un composant doit être aligné au centre et occuper toute la largeur d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T centerFill();

    /**
     *  Permet de préciser qu'un composant doit être aligné en bas et à gauche d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T bottomLeft();

    /**
     *  Permet de préciser qu'un composant doit être aligné en bas et au centre d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T bottomCenter();

    /**
     *  Permet de préciser qu'un composant doit être aligné en bas et à droite d'une cellule d'un panel ayant un
     *  GridLayout.
     */
    T bottomRight();

    /**
     *  Permet de préciser qu'un composant doit être aligné en bas et occuper toute la largeur d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T bottomFill();

    /**
     *  Permet de préciser qu'un composant doit occuper toute la hauteur et être aligné à gauche d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T fillLeft();

    /**
     *  Permet de préciser qu'un composant doit occuper toute la hauteur et être aligné au centre d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T fillCenter();

    /**
     *  Permet de préciser qu'un composant doit occuper toute la hauteur et être aligné à droite d'une cellule d'un
     *  panel ayant un GridLayout.
     */
    T fillRight();

    /**
     *  Permet de préciser qu'un composant doit occuper toute la hauteur et toute la largeur dune cellule
     *  d'un panel ayant un GridLayout.
     */
    T fillFill();

    /**
     *  Permet de préciser qu'un composant doit occuper {@code span} colonnes d'un panel ayant un GridLayout.
     */
    T colSpan(int span);

    /**
     *  Permet de préciser qu'un composant doit occuper {@code span} lignes d'un panel ayant un GridLayout.
     */
    T rowSpan(int span);

    /**
     *  Permet de préciser qu'un composant doit occuper {@code colSpan} colonnes et {@code rowSpan} lignes d'un panel
     *  ayant un GridLayout.
     */
    T span(int colSpan, int rowSpan);

    /**
     *  Permet de préciser si un composant doit être redimensionné pour occuper toute la place disponible
     *  horizontalement dans un panel ayant un GridLayout. Si plusieurs composants ont la propriété hGrow activée,
     *  ils se partageront l'espace horizontal disponible de manière proportionnelle.
     *  @param grow Indique si le composant doit être redimensionné horizontalement
     */
    T hGrow(boolean grow);

    /**
     *  Permet de préciser qu'un composant doit être redimensionné pour occuper toute la place disponible
     *  horizontalement dans un panel ayant un GridLayout. Si plusieurs composants ont la propriété hGrow activée,
     *  ils se partageront l'espace horizontal disponible de manière proportionnelle.
     */
    T hGrow();

    /**
     *  Permet de préciser si un composant doit être redimensionné pour occuper toute la place disponible
     *  verticalement dans un panel ayant un GridLayout. Si plusieurs composants ont la propriété vGrow activée,
     *  ils se partageront l'espace vertical disponible de manière proportionnelle.
     *  @param grow Indique si le composant doit être redimensionné verticalement
     */
    T vGrow(boolean grow);

    /**
     *  Permet de préciser qu'un composant doit être redimensionné pour occuper toute la place disponible
     *  verticalement dans un panel ayant un GridLayout. Si plusieurs composants ont la propriété vGrow activée,
     *  ils se partageront l'espace vertical disponible de manière proportionnelle.
     */
    T vGrow();

    /**
     *  Permet de préciser si un composant doit être redimensionné pour occuper toute la place disponible
     *  horizontalement et verticalement dans un panel ayant un GridLayout. Si plusieurs composants ont la propriété
     *  grow activée, ils se partageront l'espace disponible de manière proportionnelle.
     *  @param hGrow Indique si le composant doit être redimensionné horizontalement
     *  @param vGrow Indique si le composant doit être redimensionné verticalement
     */
    T grow(boolean hGrow, boolean vGrow);

    /**
     *  Permet de placer un composant dans la partie haute d'un panel ayant un BorderLayout.
     */
    T top();

    /**
     *  Permet de placer un composant dans la partie gauche d'un panel ayant un BorderLayout.
     */
    T left();

    /**
     *  Permet de placer un composant dans la partie centrale d'un panel ayant un BorderLayout.
     */
    T middle();

    /**
     *  Permet de placer un composant dans la partie droite d'un panel ayant un BorderLayout.
     */
    T right();

    /**
     *  Permet de placer un composant dans la partie basse d'un panel ayant un BorderLayout.
     */
    T bottom();

    /**
     *  Permet de redimensionner un composant en spécifiant le nombre de colonnes.
     *  Le nombre de lignes est considéré comme étant 1.
     *  @param columns Nombre de colonnes
     */
    T sizeTo(int columns);

    /**
     *  Permet de redimensionner un composant en spécifiant le nombre de colonnes et le nombre de lignes.
     *  @param columns Nombre de colonnes
     *  @param rows Nombre de lignes
     */
    T sizeTo(int columns, int rows);
}
