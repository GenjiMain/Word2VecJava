package com.laya.word2vec;

import java.io.Serializable;

public class SerializableSearcher extends SearcherImpl implements Serializable {

    public SerializableSearcher(SerializableWord2Vec model){
        super(new Word2VecModel(model.vocab, model.layerSize, model.vectors));
    }
}
