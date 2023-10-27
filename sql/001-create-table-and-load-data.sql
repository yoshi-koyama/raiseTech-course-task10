DROP TABLE IF EXISTS skiresort;

CREATE TABLE skiresort (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  area VARCHAR(20) NOT NULL,
  customerEvaluation VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);


INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (1, "rusutsu", "hokkaido", "雪質が良くて、カービング練習に最適。");
INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (2, "zao", "yamagata","凄く広くて、樹氷が綺麗だった。");
INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (3, "garaYuzawa", "niigata","新幹線の駅直結で便利。東京から日帰り客が多い。");
INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (4, "hakubaGoryu", "nagano", "GWまで滑れる。外国人が多くていつも混んでる。");
