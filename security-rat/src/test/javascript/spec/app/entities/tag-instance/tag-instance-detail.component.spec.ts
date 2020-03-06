import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TagInstanceDetailComponent } from 'app/entities/tag-instance/tag-instance-detail.component';
import { TagInstance } from 'app/shared/model/tag-instance.model';

describe('Component Tests', () => {
  describe('TagInstance Management Detail Component', () => {
    let comp: TagInstanceDetailComponent;
    let fixture: ComponentFixture<TagInstanceDetailComponent>;
    const route = ({ data: of({ tagInstance: new TagInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TagInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
