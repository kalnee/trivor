/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TranscriptDetailComponent } from '../../../../../../main/webapp/app/entities/transcript/transcript-detail.component';
import { TranscriptService } from '../../../../../../main/webapp/app/entities/transcript/transcript.service';
import { Transcript } from '../../../../../../main/webapp/app/entities/transcript/transcript.model';

describe('Component Tests', () => {

    describe('Transcript Management Detail Component', () => {
        let comp: TranscriptDetailComponent;
        let fixture: ComponentFixture<TranscriptDetailComponent>;
        let service: TranscriptService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TranscriptDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TranscriptService,
                    JhiEventManager
                ]
            }).overrideTemplate(TranscriptDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TranscriptDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TranscriptService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Transcript(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transcript).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
