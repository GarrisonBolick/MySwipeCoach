export interface Profile {
    id?: number,
    name?: string,
    firstName?: string,
    lastName?: string,
    gender?: string,
    age?: number,
    birthdate?: Date,
    description?: string,
    imageURL?: string,
    meetingPreference?: string,
    friends?: Profile[],
    email?: string,
    clientAutoId?: number,
    userType?: string
}

export interface ClientDto {
    id: number,
    token: string,
    firstName: string,
    lastName: string,
    dateOfBirth: Date,
    contactNumber: string,
    email: string
}

export interface Conversation {
    id: number,
    contacts: Profile[],
    messages: Message[]
}

export interface Message {
    id: number,
    author: Profile,
    value: string
}