package com.github.ardririy.mine_farming;

public class Farmland {
    Position pos;
    int moisture = 0; // 土地の水分量
    // 各栄養素の含有量
    int nitrogen = 0;   // 窒素
    int phosphorus = 0; // リン
    int kalium = 0;  // カリウム

    public Farmland(Position position) {
        this.pos = position;
    }

}
