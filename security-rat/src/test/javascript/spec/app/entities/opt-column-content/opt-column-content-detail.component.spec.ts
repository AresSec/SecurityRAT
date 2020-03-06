import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnContentDetailComponent } from 'app/entities/opt-column-content/opt-column-content-detail.component';
import { OptColumnContent } from 'app/shared/model/opt-column-content.model';

describe('Component Tests', () => {
  describe('OptColumnContent Management Detail Component', () => {
    let comp: OptColumnContentDetailComponent;
    let fixture: ComponentFixture<OptColumnContentDetailComponent>;
    const route = ({ data: of({ optColumnContent: new OptColumnContent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnContentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OptColumnContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptColumnContentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optColumnContent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optColumnContent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
