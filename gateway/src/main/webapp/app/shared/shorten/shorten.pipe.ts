import {Pipe, PipeTransform} from '@angular/core';

const DEFAULT_LENGTH = 15;

@Pipe({
    name: 'shorten'
})
export class ShortenPipe implements PipeTransform {

    transform(value: any, length: number): any {
        length = length || DEFAULT_LENGTH;
        if (value.length > length) {
            return value.substr(0, length) + '...';
        }
        return value;
    }

}
