import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnComponent } from 'app/entities/opt-column/opt-column.component';
import { OptColumnService } from 'app/entities/opt-column/opt-column.service';
import { OptColumn } from 'app/shared/model/opt-column.model';

describe('Component Tests', () => {
  describe('OptColumn Management Component', () => {
    let comp: OptColumnComponent;
    let fixture: ComponentFixture<OptColumnComponent>;
    let service: OptColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnComponent]
      })
        .overrideTemplate(OptColumnComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OptColumn(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.optColumns && comp.optColumns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
