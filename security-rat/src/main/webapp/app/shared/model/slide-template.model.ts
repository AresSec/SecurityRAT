export interface ISlideTemplate {
  id?: number;
  name?: string;
  description?: string;
  content?: string;
}

export class SlideTemplate implements ISlideTemplate {
  constructor(public id?: number, public name?: string, public description?: string, public content?: string) {}
}
