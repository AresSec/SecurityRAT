import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { SlideTemplateDetailComponent } from 'app/entities/slide-template/slide-template-detail.component';
import { SlideTemplate } from 'app/shared/model/slide-template.model';

describe('Component Tests', () => {
  describe('SlideTemplate Management Detail Component', () => {
    let comp: SlideTemplateDetailComponent;
    let fixture: ComponentFixture<SlideTemplateDetailComponent>;
    const route = ({ data: of({ slideTemplate: new SlideTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [SlideTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SlideTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SlideTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load slideTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.slideTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
