﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.Networking.Match;
using UnityEngine.SceneManagement;


public class CustomNetworkManager : NetworkManager {

    public int buyIn;

    public void CreateMatch(string name, string password, int buyIn) {

        LoadGame();

        singleton.StopMatchMaker();

        this.buyIn = buyIn;

        bool advertise = true;
        if (!password.Equals("")) {
            advertise = false;
        }

        singleton.StartMatchMaker();
        singleton.matchMaker.CreateMatch(name, 10, advertise, password, "", "", 0, 0, OnMatchCreate);
    }
    public void QuickJoin() {
        singleton.StopMatchMaker();

        singleton.StartMatchMaker();
        
        singleton.matchMaker.ListMatches(0, 10, "", true, 0, 0, FoundMatchList);

    }
    public void FoundMatchList(bool success, string extendedInfo, List<MatchInfoSnapshot> matches) {

        if (!success) { return; }

        foreach (MatchInfoSnapshot match in matches) {

            if (match.currentSize < match.maxSize) {
                JoinMatch(match, string.Empty);
                break;
            }

        }

    }
    public void JoinMatch(MatchInfoSnapshot match, string password) {
        LoadGame();
        singleton.matchMaker.JoinMatch(match.networkId, password, "", "", 0, 0, OnMatchJoined);
    }

    private void LoadGame() {
        SceneManager.LoadScene("Game");
    }
}
