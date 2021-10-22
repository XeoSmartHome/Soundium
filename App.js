/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { useEffect, useState } from "react";
import { Button, NativeModules, SafeAreaView, StyleSheet, NativeEventEmitter, Text } from "react-native";
const {PlaylistManagerModule} = NativeModules;


const App = () => {

    const [lastEvent, setLastEvent] = useState();

    useEffect(
        () => {
            const bridge = new NativeEventEmitter(PlaylistManagerModule);
            const a = bridge.addListener('myEvent', console.log);
            const b = bridge.addListener('myEvent', setLastEvent);
            return () => {
                bridge.removeAllListeners(PlaylistManagerModule);
            }
        },
        []
    );

    return (
        <SafeAreaView style={{alignItems: "center", justifyContent: "center", flex: 1}}>
            <Text>
                {
                    JSON.stringify(lastEvent)
                }
            </Text>
        </SafeAreaView>
    );
};


const styles = StyleSheet.create({

});

export default App;
