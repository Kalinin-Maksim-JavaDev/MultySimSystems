package Model;

import Logic.IGameData;
import Logic.Model.IDataSource;
import Model.Game.GameData;


public class DataSourceFromBinaryData implements IDataSource {

    GameData gamedata;

    public DataSourceFromBinaryData(byte[] data_) {

        gamedata = new GameData(data_);
    }

    public IGameData GetData() {
        return gamedata;
    }
}
