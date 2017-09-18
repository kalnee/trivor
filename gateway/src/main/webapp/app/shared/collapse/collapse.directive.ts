import {Directive, ElementRef, HostListener, Renderer2} from '@angular/core';

@Directive({
    selector: '[jhiCollapse]'
})
export class CollapseDirective {

    @HostListener('click')
    collapse() {
        const content = this.element.nativeElement.parentNode.parentNode.nextSibling.nextElementSibling;
        const open = content.classList.contains('show');
        if (open) {
            this.renderer.removeClass(content, 'show');
        } else {
            this.renderer.addClass(content, 'show');
        }
    }

    constructor(private element: ElementRef,
                private renderer: Renderer2) {
    }
}
