import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';

type EntityResponseType = HttpResponse<ITrainingCustomSlideNode>;
type EntityArrayResponseType = HttpResponse<ITrainingCustomSlideNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingCustomSlideNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-custom-slide-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-custom-slide-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingCustomSlideNode: ITrainingCustomSlideNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingCustomSlideNode>(this.resourceUrl, trainingCustomSlideNode, { observe: 'response' });
  }

  update(trainingCustomSlideNode: ITrainingCustomSlideNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingCustomSlideNode>(this.resourceUrl, trainingCustomSlideNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingCustomSlideNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingCustomSlideNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingCustomSlideNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
