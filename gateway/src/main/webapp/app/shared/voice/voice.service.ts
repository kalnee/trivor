import {Injectable} from '@angular/core';
import Artyom from 'artyom.js/build/artyom';

@Injectable()
export class VoiceService {
    readonly artyom = new Artyom();

    constructor() {
        console.log('\n\nVOICE SERVICE INITIALIZED\n\n');
    }

    public init() {
        this.artyom.initialize({
            lang: 'en-US', // American english
            continuous: true, // Listen forever
            smart: true,
            soundex: true, // Use the soundex algorithm to increase accuracy
            debug: true, // Show messages in the console
            executionKeyword: 'and do it now',
            listen: true, // Start to listen commands !
            obeyKeyword: 'start again'
        }).then(() => {
            console.log('Artyom has been successfully initialized');
        }).catch((err) => {
            console.error('Artyom could\'nt be initialized: ', err);
        });
    }

    public say(message: string) {
        this.artyom.say(message);
    }

    public destroy() {
        this.artyom.fatality().then(() => {
            console.log('Artyom successfully stopped!');
        });
    }
}
