import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITraining } from 'app/shared/model/training.model';

type EntityResponseType = HttpResponse<ITraining>;
type EntityArrayResponseType = HttpResponse<ITraining[]>;

@Injectable({ providedIn: 'root' })
export class TrainingService {
  public resourceUrl = SERVER_API_URL + 'api/trainings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/trainings';

  constructor(protected http: HttpClient) {}

  create(training: ITraining): Observable<EntityResponseType> {
    return this.http.post<ITraining>(this.resourceUrl, training, { observe: 'response' });
  }

  update(training: ITraining): Observable<EntityResponseType> {
    return this.http.put<ITraining>(this.resourceUrl, training, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITraining>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITraining[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITraining[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
