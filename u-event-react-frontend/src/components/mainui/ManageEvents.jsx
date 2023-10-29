// I used copilot and chatgpt for assistance //


import React, { useState, useEffect } from 'react';
import EventCard2 from './EventCard2';
import '../../css/ManageEvents.css';

const ManageEvents = () => {
    const [events, setEvents] = useState([]);
    const [editingEvent, setEditingEvent] = useState(null); // State to hold the event being edited

    useEffect(() => {
        fetch('http://localhost:8080/api/events')
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch events.');
                }
                return res.json();
            })
            .then((data) => {
                setEvents(data);
            })
            .catch((error) => {
                console.error("Error fetching events:", error);
            });
    }, []);

    const deleteEvent = (id) => {
        fetch(`http://localhost:8080/api/events/${id}`, {
            method: 'DELETE',
        })
        .then((res) => {
            if (!res.ok) {
                throw new Error('Failed to delete event.');
            }
            const updatedEvents = events.filter((event) => event.eventId !== id);
            setEvents(updatedEvents);
        })
        .catch((error) => {
            console.error("Error deleting event:", error);
        });
    }

    const startEditingEvent = (id) => {
        const eventToEdit = events.find(event => event.eventId === id);
        setEditingEvent(eventToEdit);
    }

    const editSubmit = (updatedEvent) => {
        fetch(`http://localhost:8080/api/events/${updatedEvent.eventId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedEvent),
        })
        .then((res) => {
            if (!res.ok) {
                throw new Error('Failed to update event.');
            }
            const updatedEvents = events.map((event) => {
                if (event.eventId === updatedEvent.eventId) {
                    event = updatedEvent;
                }
                return event;
            });
            setEvents(updatedEvents);
            setEditingEvent(null);
        })
        .catch((error) => {
            console.error("Error updating event:", error);
        });
    }

    return (
        <div className="manage-events-container">
            <div className="manage-events-main-container">
                <div className="manage-events-header">
                    <h1>Manage Events</h1>
                </div>
                <div className="manage-events-body">
                    <div className="manage-events-body-left">
                        {/* If you have a component to place here, add it and pass the necessary props */}
                    </div>
                    <div className="manage-events-body-right">
                        {events.map((event, idx) => (
                            <div key={idx}>
                                <EventCard2 {...event} />
                                <button onClick={() => deleteEvent(event.eventId)}>Delete</button>
                                <button onClick={() => startEditingEvent(event.eventId)}>Edit</button>
                            </div>  
                        ))}
                    </div>
                </div>
                {editingEvent && (
                    <div>
                        <label htmlFor="eventName"> Event Name:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventName}
                            onChange={(e) => setEditingEvent({...editingEvent, eventName: e.target.value})}
                        />
                        <label htmlFor="eventDescription"> Event Description:</label>
                        <input

                            type="text"
                            value={editingEvent.eventDescription}
                            onChange={(e) => setEditingEvent({...editingEvent, description: e.target.value})}
                        />
                        <label htmlFor="eventAddress"> Event Address:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventAddress}
                            onChange={(e) => setEditingEvent({...editingEvent, eventAddress: e.target.value})}
                        />
                        <label htmlFor="eventCity"> Event City:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventCity}
                            onChange={(e) => setEditingEvent({...editingEvent, eventCity: e.target.value})}
                        />                        
                        <label htmlFor="eventZip"> Event Zip:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventZip}
                            onChange={(e) => setEditingEvent({...editingEvent, eventZip: e.target.value})}
                        />
                        <label htmlFor="eventDate"> Event Date:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventDate}
                            onChange={(e) => setEditingEvent({...editingEvent, eventDate: e.target.value})}
                        />
                        <label htmlFor="eventTime"> Event Time:</label>
                        <input 
                            type="text"
                            value={editingEvent.eventTime}
                            onChange={(e) => setEditingEvent({...editingEvent, eventTime: e.target.value})}
                        />
                        <button onClick={() => editSubmit(editingEvent)}>Submit Edit</button>
                    </div>
                )}
            </div>
        </div>
    )
}

export default ManageEvents;
